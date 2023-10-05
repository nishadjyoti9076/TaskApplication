package com.example.taskapplication.ui.main.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.di_3rddaggerwithmvvmapp.db.PostDB
import com.example.di_3rddaggerwithmvvmapp.viewmodels.MainViewModel
import com.example.di_3rddaggerwithmvvmapp.viewmodels.MainViewModelFactory
import com.example.firebaseapp.crudwithoutdi.adapters.ProductAdapter
import com.example.taskapplication.R
import com.example.taskapplication.databinding.FragmentHomeBinding
import com.example.taskapplication.models.Products
import com.example.taskapplication.ui.main.MainActivity
import com.example.taskapplication.utils.PostsApplication
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var mainViewModel: MainViewModel
    @Inject
    lateinit var fakerDB1: PostDB
    @Inject
    lateinit var mainviewmodelfactory : MainViewModelFactory
    lateinit var adapter:ProductAdapter

    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var navController : NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        (requireActivity().application as PostsApplication).applicationcomponent.inject(this)  // this is call field injection
        mainViewModel=ViewModelProvider(this,mainviewmodelfactory).get(MainViewModel::class.java)
        mainViewModel.productlivadata.observe(requireActivity()){
                    if (!it.isNullOrEmpty()){
                        Toast.makeText(requireContext(), "Product load success", Toast.LENGTH_LONG).show()
                        bindList(it)
                        adapter.setData(it)
                    }
                }
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.bottom_nav_menu, menu);

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
         if (id == R.id.item1) {
            /* val intent=Intent(requireContext(),MainActivity::class.java)
             requireContext().startActivity(intent)*/
            navController.navigate(R.id.action_homeFragment_to_audioRecordFragment)
        } else {

        }

        return super.onOptionsItemSelected(item)

    }

    fun bindList(list: List<Products>){
        adapter = ProductAdapter(requireContext(),list)
        binding.recyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.recyclerView.adapter=adapter
    }
}