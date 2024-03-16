package com.example.trackmybus.Interactions

import com.example.trackmybus.Responses.Model.User

interface Selector {
fun state(user: User)
}