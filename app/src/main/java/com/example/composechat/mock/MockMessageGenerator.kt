package com.example.composechat.mock

import com.example.composechat.feature.conversation.model.Message
import java.util.*

object MockMessageGenerator {
    val conversationSample = listOf(
        Message(
            text = "Test...Test...Test..."
        ),
        Message(
            text = "Shopping list:\n" +
                    "Dog food\n" +
                    "Eggs\n" +
                    "H20\n" +
                    "Chemistry set\n" +
                    "Grim Fandango\n" +
                    "Toast of London\n" +
                    "Bread\n" +
                    "Nunchucks\n" +
                    "Xmas decorations\n"
        ),
        Message(
            text = "Whats up?.\n" +
                    "I'm totally not a stalker!",
            isMe = true
        ),
        Message(
            text = "K i trust you..."
        ),
        Message(
            text = "Hey, take a look at the Moon, it's on fire!\n" +
                    "It's the aliens from Independence Day 2 - the cringy sequel.\n" +
                    "It's sooooo baaaaaad. Essentially ruined the name\n" +
                    "Anyhow, see you soon :)"
        ),
        Message(
            text = "I'm just gonna drop by my friend :)"
        ),
        Message(
            text = "Ok, I'm writing a book. Also, where have you been all my life?",
            isMe = true
        ),
        Message(
            text = "Brb"
        ),
        Message(
            text = "I finished writing my book. It's bad. I've set it on fire ^_^",
            isMe = true
        ),
        Message(
            text = "I didn't know you can burn the entire building down by setting a book on fire"
        ),
        Message(
            text = "That's a first. Looks like the police are coming..."
        ),
        Message(
            text = "Oh noes, stay safe!",
            isMe = true
        ),
        Message(
            text = "They nicked the neighbour for it. Sleep tight"
        ),
    )

    var randomReplyMessage = Message(
        text = "I can only talk like this. Good luck! ${UUID.randomUUID().toString()}"
    )
}