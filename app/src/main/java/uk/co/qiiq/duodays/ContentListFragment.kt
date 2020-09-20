package uk.co.qiiq.duodays

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_content_list.*
import uk.co.qiiq.duodays.model.Content
import uk.co.qiiq.duodays.viewmodel.ListContentViewModel

class ContentListFragment : Fragment() {

    private val deleteTapped: (String) -> Unit = {
        viewModel.deleteContent(it)
    }
    private val contentItems = mutableListOf<Content>()
    private val viewModel: ListContentViewModel by viewModels()

    private lateinit var viewAdapter: ContentAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_content_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewManager = LinearLayoutManager(requireContext())
        viewAdapter = ContentAdapter(deleteTapped)
        recyclerview_content.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewModel.getContent().observe(requireActivity()) {
            it.let {
                contentItems.clear()
                contentItems.addAll(it)

                textview_no_content?.visibility = if (contentItems.any()) {
                    View.GONE
                } else {
                    View.VISIBLE
                }

                recyclerview_content?.visibility = if (contentItems.any()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                viewAdapter.updateContent(contentItems)
            }
        }

        fab_add_content.setOnClickListener {
            findNavController().navigate(R.id.action_ContentListFragment_to_AddContentFragment)
        }
    }
}