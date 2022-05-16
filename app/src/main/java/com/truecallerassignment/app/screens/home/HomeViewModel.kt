package com.truecallerassignment.app.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truecallerassignment.common.Constant.DEFAULT_STRING
import com.truecallerassignment.common.Resource
import com.truecallerassignment.domain.use_cases.GetDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject
import kotlin.collections.HashMap

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase
) :
    ViewModel() {

    // 10th Character Request state object
    private val _tenthCharacter = mutableStateOf<HomeState>(HomeState())
    val tenthCharacter: State<HomeState> = _tenthCharacter


    // Every 10th Character Request state object
    private val _everyTenthCharacter = mutableStateOf<HomeState>(HomeState())
    val everyTenthCharacter: State<HomeState> = _everyTenthCharacter


    // Word counter request
    private val _wordCount = mutableStateOf<HomeState>(HomeState())
    val wordCount: State<HomeState> = _wordCount


    var get10thCharacter: String = "1. Truecaller10thCharacterRequest:\n"

    var every10thNumberArrayValues: String = "2. TruecallerEvery10thCharacterRequest:\n"

    var wordCountResponse = ""

    /*.. This call will fetch content
    *....and give the 10th character
    ...
    * */
    fun get10thCharacterValue() {
        getDataUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _tenthCharacter.value = HomeState(isLoading = true)
                }
                is Resource.Success -> {

                    get10thCharacter += it.data!!.replace("\\s".toRegex(), " ")[10 - 1]
                    get10thCharacter += "\n\n"

                    _tenthCharacter.value = HomeState(data = get10thCharacter)
                }
                is Resource.Error -> {
                    _tenthCharacter.value = HomeState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)

    }

    /*.. This call will fetch content
    *....and give the every 10th character
    .... available in the stream
    * */
    fun getEveryTenthCharacter() {
        getDataUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _everyTenthCharacter.value = HomeState(isLoading = true)
                }
                is Resource.Success -> {



                    every10thNumberArrayValues += every10th(
                        it.data!!.replace("\\s".toRegex(), ""), 10
                    )!!.map { it.toString() }.toTypedArray().toList().toString()

                    every10thNumberArrayValues += "\n\n"

                    _everyTenthCharacter.value = HomeState(data = it.data)

                }
                is Resource.Error -> {
                    _everyTenthCharacter.value = HomeState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)

    }

    /**...This will fetch
       ...and give the unique and repeated words
     */
    fun repeatedWords() {
        getDataUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _wordCount.value = HomeState(isLoading = true)
                }
                is Resource.Success -> {
                    val values = printUniqueAndRepeatedWords(it.data!!.split(" ").toString())

                    val sb = StringBuilder()
                    sb.append(
                        "\n3. TruecallerWordCounterRequest:\n"
                    ).append("Unique Words \n\n").append(values.first).append("\n\n Repeated words \n\n")
                        .append(values.second)

                    wordCountResponse = sb.toString()
                    _wordCount.value = HomeState(data = sb.toString())

                }
                is Resource.Error -> {
                    _wordCount.value = HomeState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)

    }

    /**...break unique words and append in mutable list
       ...repeated words and adds in mutable list
     */
    private suspend fun printUniqueAndRepeatedWords(str: String?): Pair<String, String> {

        val uniqueWords: MutableList<String> = mutableListOf()
        val repeatedWords: MutableList<String> = mutableListOf()

        var uniqueWordsString = ""
        var repeatedWordsString = ""

        withContext(Dispatchers.Default)
        {

            val pattern: Pattern = Pattern.compile("[a-zA-Z]+")
            val matcher: Matcher = pattern.matcher(str!!.lowercase(Locale.US))

            val hashmap: HashMap<String, Int> = HashMap()

            while (matcher.find()) {
                val word: String = matcher.group()

                if (!hashmap.containsKey(word)) {
                    hashmap[word] = 1
                } else {
                    hashmap[word] = hashmap[word]!! + 1
                }
            }

            val sets: Set<String> = hashmap.keys
            val iterator = sets.iterator()
            while (iterator.hasNext()) {
                val w = iterator.next()
                if (hashmap[w] == 1) {
                    uniqueWords.add(w)
                } else {
                    repeatedWords.add(w + " = repeated " + hashmap[w] + " times")
                }
            }
        }

        uniqueWords.forEach {
            uniqueWordsString += "-> $it = characters count ${it.length}\n"
        }

        repeatedWords.forEach {
            repeatedWordsString += "-> $it\n"
        }

        return Pair(uniqueWordsString, repeatedWordsString)
    }


    /**
    Find evert 10th character and display it on the screen
     */
    private suspend fun every10th(str: String, nthCharacter: Int): String? {
        var displayNthCharacter: String? = DEFAULT_STRING
        withContext(Dispatchers.Default)
        {
            var i = nthCharacter - 1
            while (i < str.length) {
                displayNthCharacter += str[i] + " "
                i += nthCharacter
            }
        }
        return displayNthCharacter
    }
}