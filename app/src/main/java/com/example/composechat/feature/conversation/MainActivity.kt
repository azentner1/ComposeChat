package com.example.composechat.feature.conversation

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composechat.R
import com.example.composechat.core.ext.dayFromTimestamp
import com.example.composechat.core.ext.timeFromTimestamp
import com.example.composechat.feature.conversation.model.Message
import com.example.composechat.core.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeChatTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainActivityContent()
                }
            }
        }
    }
}

@Composable
fun MainActivityContent(viewModel: MainViewModel = hiltViewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        val messageList = viewModel.messageList
        TopAppBar(title = {
            Image(
                painter = painterResource(R.drawable.android_image),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Droid",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }, backgroundColor = Color.White, navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_chevron_left),
                    contentDescription = "",
                    tint = Primary
                )
            }
        })
        Spacer(modifier = Modifier.height(8.dp))
        Conversation(Modifier.weight(1f), messageList, viewModel)
        InputComponent(viewModel)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Conversation(modifier: Modifier, messages: List<Message>, viewModel: MainViewModel) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LazyColumn(modifier = modifier, state = listState) {
        itemsIndexed(messages) { index, message ->
            if (viewModel.showDateHeader(index, messages)) {
                DateStamp(timestamp = message.timestamp)
            }
            MessageCard(message, viewModel.checkHasTail(index, messages))
        }
        if (messages.isNotEmpty()) {
            scope.launch {
                listState.animateScrollToItem(messages.indices.last)
            }
        }

    }
}

@Composable
fun DateStamp(timestamp: Long) {
    Row(modifier = Modifier.fillMaxWidth().padding(4.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = timestamp.dayFromTimestamp(),
            style = MaterialTheme.typography.body2,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = timestamp.timeFromTimestamp(),
            style = MaterialTheme.typography.caption,
            color = Color.LightGray
        )
    }
}

@Composable
fun InputComponent(viewModel: MainViewModel) {
    var text by rememberSaveable { mutableStateOf("") }
    var focusedBorderColor by remember { mutableStateOf(Color.Gray) }
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .background(color = Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.weight(1f), shape = RoundedCornerShape(32.dp),
                border = BorderStroke(width = 1.dp, color = focusedBorderColor)
            ) {
                TextField(
                    modifier = Modifier.onFocusChanged {
                        focusedBorderColor = if (it.isFocused) Primary else Color.Gray
                    },
                    value = text, onValueChange = {
                        text = it
                    }, label = { Text(stringResource(R.string.message)) }, colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Surface(shape = CircleShape) {
                Image(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                listOf(SendGradientTop, SendGradientBottom)
                            )
                        )
                        .padding(6.dp)
                        .clickable {
                            viewModel.addMessage(text)
                        },
                    painter = painterResource(id = R.drawable.ic_message_send),
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
fun MessageCard(msg: Message, hasTail: Boolean) {
    val horizontalContentArrangement = if (msg.isMe) Arrangement.End else Arrangement.Start
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp), horizontalArrangement = horizontalContentArrangement
    ) {
        Surface(
            modifier = Modifier.padding(
                start = if (msg.isMe) 56.dp else 0.dp,
                end = if (msg.isMe) 0.dp else 56.dp
            ),
            shape = RoundedCornerShape(
                topEnd = 8.dp,
                topStart = 8.dp,
                bottomEnd = if(hasTail && msg.isMe) 0.dp else 8.dp,
                bottomStart = if(hasTail && !msg.isMe) 0.dp else 8.dp
            ), elevation = 1.dp, color = if (msg.isMe) Primary else ChatBubbleNotMe
        ) {
            when {
                msg.isMe -> {
                    MessageContentMe(message = msg)
                }
                else -> {
                    MessageContentNotMe(message = msg)
                }
            }
        }
    }
}

@Composable
fun MessageContentMe(message: Message) {
    Column(modifier = Modifier.padding(all = 8.dp), horizontalAlignment = Alignment.End) {
        Text(
            text = message.text,
            style = MaterialTheme.typography.body2,
            color = Color.White
        )
        Image(
            modifier = Modifier.size(12.dp),
            painter = painterResource(id = R.drawable.ic_double_tick),
            contentDescription = ""
        )
    }
}

@Composable
fun MessageContentNotMe(message: Message) {
    Column(modifier = Modifier.padding(all = 8.dp)) {
        Text(
            text = message.text,
            style = MaterialTheme.typography.body2,
            color = Color.Gray
        )
    }
}


@Preview(
    showBackground = true
)

@Composable
fun PreviewConversation() {
    ComposeChatTheme {
        DateStamp(4342423424234)
    }
}