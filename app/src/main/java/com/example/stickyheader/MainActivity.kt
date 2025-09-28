package com.example.stickyheader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stickyheader.ui.theme.StickyHeaderTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { StickyHeaderTheme { ContactListApp() } }
    }
}

@Composable
fun ContactListApp() {
    ContactListScreen(modifier = Modifier.fillMaxSize())
}

/** 50 names (no duplicates) */
private fun sampleContacts(): List<String> {
    val firsts = listOf(
        "James","Mary","Robert","Patricia","John","Jennifer","Michael","Linda","William","Elizabeth",
        "David","Barbara","Richard","Susan","Joseph","Jessica","Thomas","Sarah","Charles","Karen",
        "Christopher","Nancy","Daniel","Lisa","Matthew","Betty","Anthony","Margaret","Mark","Sandra",
        "Donald","Ashley","Steven","Kimberly","Paul","Emily","Andrew","Donna","Joshua","Michelle",
        "Kenneth","Dorothy","Kevin","Carol","Brian","Amanda","George","Melissa","Jackie","Deborah"
    )
    val lasts = listOf(
        "Smith","Johnson","Williams","Brown","Jones","Garcia","Miller","Davis","Rodriguez","Martinez",
        "Hernandez","Lopez","Gonzalez","Wilson","Anderson","Thomas","Taylor","Moore","Jackson","Martin",
        "Lee","Perez","Thompson","White","Harris","Sanchez","Clark","Ramirez","Lewis","Robinson",
        "Walker","Young","Allen","King","Wright","Scott","Torres","Nguyen","Hill","Flores",
        "Green","Adams","Nelson","Baker","Hall","Rivera","Campbell","Mitchell","Taylor","Roberts"
    )
    return firsts.zip(lasts).map { (f, l) -> "$f $l" }.sorted()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactListScreen(modifier: Modifier = Modifier) {
    val contacts = remember { sampleContacts() }
    val grouped = remember(contacts) {
        contacts.groupBy { it.firstOrNull()?.uppercaseChar() ?: '#' }.toSortedMap()
    }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val showFab by remember { derivedStateOf { listState.firstVisibleItemIndex > 10 } }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            if (showFab) {
                FloatingActionButton(onClick = { scope.launch { listState.animateScrollToItem(0) } }) {
                    Text("Scroll to Top")
                }
            }
        }
    ) { inner ->
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(inner)
        ) {
            grouped.forEach { (letter, names) ->
                // Sticky header with solid background so it won't overlap visually
                stickyHeader {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        tonalElevation = 2.dp
                    ) {
                        Text(
                            text = "  $letter",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                items(names) { name ->
                    Text("  $name", modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp))
                }
                // Space after each alphabet section
                item { Spacer(Modifier.height(12.dp)) }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewContactList() {
    StickyHeaderTheme { ContactListApp() }
}
