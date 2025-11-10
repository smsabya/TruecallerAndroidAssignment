package com.sabya.truecallerandroidassignment.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sabya.truecallerandroidassignment.presentation.viewmodel.ComposeViewModel
import com.sabya.truecallerandroidassignment.utils.Resource

@Composable
fun TruecallerAssignmentScreen(viewModel: ComposeViewModel) {
    val state by viewModel.taskState.collectAsState()
    val result1 by viewModel.char15Result.collectAsState()
    val result2 by viewModel.every15Result.collectAsState()
    val result3 by viewModel.wordCounterResult.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = "15th character: $result1",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = "Every 15th: $result2",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth().height(80.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = "Word counts:\n$result3",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth().height(100.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { viewModel.performTasks() },
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text(text = "Load Content")
            }
            Spacer(modifier = Modifier.height(10.dp))
            when (state) {
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
                    )
                }

                is Resource.Error -> {
                    Text(
                        text = (state as Resource.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
                    )
                }

                else -> {}
            }
        }
    }
}



