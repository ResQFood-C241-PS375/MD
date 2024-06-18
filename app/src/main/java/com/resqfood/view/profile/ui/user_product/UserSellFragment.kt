package com.resqfood.view.profile.ui.user_product

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.resqfood.ViewModelFactory
import com.resqfood.data.adapter.ForSaleProfileAdapter
import com.resqfood.data.response.UserSell
import com.resqfood.databinding.FragmentUserSellBinding
import com.resqfood.view.profile.ProfileViewModel

class UserSellFragment : Fragment() {

    private var _binding: FragmentUserSellBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserSellBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupUI()

    }

    private fun setupObservers(){
        viewModel.getSellUser()
        viewModel.sellUser.observe(viewLifecycleOwner) { userSell ->
            userSell?.let {
                updateSellList(it)
            }
        }
        viewModel.deleteSell.observe(viewLifecycleOwner) { deleteResult ->
            deleteResult?.let {
                if (it.message == "Penjualan berhasil dihapus!") {
                    Toast.makeText(requireActivity(), "Product deleted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(), "Failed to delete product: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateSellList(userSell: UserSell){
        val adapter = ForSaleProfileAdapter(viewModel)
        adapter.submitList(userSell.sells)
        adapter.setOnItemClickCallback(object : ForSaleProfileAdapter.OnItemClickCallback {
            override fun onItemClicked(id: String) {
                AlertDialog.Builder(requireActivity())
                    .setTitle("Konfirmasi Hapus")
                    .setMessage("Apakah Anda yakin ingin menghapus donasi ini?")
                    .setPositiveButton("Hapus") { _, _ ->
                        viewModel.deleteSell(id)
                    }
                    .setNegativeButton("Batal", null)
                    .show()
            }
        })
        binding.rvsaleProfile.adapter = adapter
        binding.rvsaleProfile.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun setupUI(){
        setupRVSellProfile()
    }
    private fun setupRVSellProfile() {
        binding.rvsaleProfile.layoutManager = LinearLayoutManager(requireActivity())
    }
}