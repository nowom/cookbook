package nowowiejski.michal.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import nowowiejski.michal.model.Tag

@Composable
internal fun TagSelection(
    tags: List<Tag>,
    newTag: String,
    onTagSelected: (Pair<Tag, Boolean>) -> Unit,
    onNewTagChanged: (String) -> Unit,
    onNewTagSubmit: () -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items(items = tags) { tag ->
            TagItem(
                tag = tag,
                onTagClicked = onTagSelected
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagItem(tag: Tag, onTagClicked: (Pair<Tag, Boolean>) -> Unit) {
    var selected by remember {
        mutableStateOf(false)
    }
    FilterChip(
        modifier = Modifier.padding(horizontal = 4.dp),
        onClick = {
            selected = !selected
            onTagClicked(Pair(tag, selected))
        },
        label = {
            Text(tag.name)
        },
        selected = selected,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
    )
}

@Composable
fun NewTagInput(
    newTag: String,
    onNewTagChanged: (String) -> Unit,
    onNewTagSubmit: () -> Unit
) {
    OutlinedTextField(
        value = newTag,
        onValueChange = onNewTagChanged,
        label = { Text("Enter new tag") },
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .height(IntrinsicSize.Min)
            .background(Color.Transparent),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onNewTagSubmit() }
        )
    )
}
