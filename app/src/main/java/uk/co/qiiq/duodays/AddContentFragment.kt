package uk.co.qiiq.duodays

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_content.*
import uk.co.qiiq.duodays.model.ContentResponse
import uk.co.qiiq.duodays.viewmodel.AddContentViewModel

class AddContentFragment : Fragment() {
    private val viewModel: AddContentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        input_layout_title_text.editText?.doOnTextChanged { _, _, _, _ ->
            run {
                input_layout_title_text.error = null
            }
        }

        input_layout_content_text.editText?.doOnTextChanged { _, _, _, _ ->
            run {
                input_layout_content_text.error = null
            }
        }

        button_upload.setOnClickListener {
            val imm =
                requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            val titleText = input_layout_title_text.editText?.text ?: ""
            val contentText = input_layout_content_text.editText?.text ?: ""

            when (viewModel.saveContent(titleText.toString(), contentText.toString())) {
                ContentResponse.Success -> findNavController().navigate(R.id.action_AddContentFragment_to_ContentListFragment)

                ContentResponse.EmptyTitle -> input_layout_title_text.error =
                    requireContext().getString(
                        R.string.edit_text_empty
                    )

                ContentResponse.EmptyContent -> input_layout_content_text.error =
                    requireContext().getString(
                        R.string.edit_text_empty
                    )
            }
        }
    }
}