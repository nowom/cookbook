package nowowiejski.michal.feature

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@Composable
fun AddStepDialog(
    onAddStep: (String) -> Unit,
    onDismiss: () -> Unit,
    newStepDescription: String,
    onDescriptionChange: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Recipe Step") },
        text = {
            TextField(
                value = newStepDescription,
                onValueChange = onDescriptionChange,
                label = { Text("Step Description") }
            )
        },
        confirmButton = {
            Button(onClick = {
                onAddStep(newStepDescription)
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}