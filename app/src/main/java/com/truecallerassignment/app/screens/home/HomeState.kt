package com.truecallerassignment.app.screens.home

data class HomeState(
    var isLoading:Boolean=false,
    var data:String?=null,
    var error:String=""
)