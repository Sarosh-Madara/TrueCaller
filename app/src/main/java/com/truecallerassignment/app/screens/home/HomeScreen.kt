package com.truecallerassignment.app.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    /*... init calling get10thCharacterValue,
    *... getEveryTenthCharacter &
    * .. repeatedWords over viewmodel
    * .. and listen the results and reflect
    * */
    LaunchedEffect(Unit, block = {
        viewModel.get10thCharacterValue()
        viewModel.getEveryTenthCharacter()
        viewModel.repeatedWords()
    })

    val lazyListState = rememberLazyListState()
    
    LazyColumn(modifier = Modifier
        .padding(15.dp)
        .fillMaxWidth(), state = lazyListState ){
        item {
            if (viewModel.tenthCharacter.value.error.isEmpty()) {


                Box(modifier = Modifier) {
                    Text(
                        text = viewModel.get10thCharacter.toString(),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            if (viewModel.everyTenthCharacter.value.error.isEmpty()) {


                Box() {
                    Text(
                        text = viewModel.every10thNumberArrayValues.toString(),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            if (viewModel.wordCount.value.error.isEmpty()) {


                Box() {
                    Text(
                        text = viewModel.wordCountResponse.toString(),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
