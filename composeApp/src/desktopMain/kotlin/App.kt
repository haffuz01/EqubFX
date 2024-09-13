import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.text.font.FontWeight
import kotlin.random.Random

@Preview
@Composable
fun App() {

    val currentScreen = remember { mutableStateOf("welcome") }
    val members = remember { mutableStateListOf("", "", "") }
    val contribution = remember { mutableStateOf(500) }
    val reward = remember { mutableStateOf(0) }



    MaterialTheme {
        when (currentScreen.value) {
            "welcome" -> WelcomeScreen(currentScreen = currentScreen)
            "detail" -> DetailScreen(
                members = members,
                currentScreen = currentScreen,
                contribution = contribution,
                reward = reward
            )

            "summary" -> SummaryScreen(
                members = members,
                contribution = contribution,
                reward = reward,
                currentScreen = currentScreen
            )
        }
    }
}


@Composable
fun WelcomeScreen(currentScreen: MutableState<String>) {

    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            "EqubFX",
            style = TextStyle(fontSize = 40.sp, color = Color.White),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.DarkGray)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp, bottom = 8.dp)
        )
        OutlinedButton(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally),
            onClick = { currentScreen.value = "detail" }
        )
        {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add new Euqb",
                tint = Color.DarkGray,
                modifier = Modifier.size(40.dp),
            )
            Text(
                text = "New Equb",
                fontSize = 40.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun DetailScreen(
    members: MutableList<String>,
    contribution: MutableState<Int>,
    currentScreen: MutableState<String>,
    reward: MutableState<Int>
) {
    reward.value = contribution.value * members.size

    fun filterdMembers(): Boolean {
        for (member in members)
            if (member == "") return false
        return true
    }

    Column(
        modifier = Modifier
            .padding(24.dp)
    ) {
        Text(
            "Equb details",
            style = TextStyle(fontSize = 40.sp, color = Color.White),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.DarkGray)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp, bottom = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Card(modifier = Modifier.padding(32.dp))
            {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth(0.5f)
                ) {
                    Text("Members (3 to 8)")
                    Surface {
                        LazyColumn(
                            modifier = Modifier.height(320.dp)
                        ) {
                            items(members.size) { index ->
                                OutlinedTextField(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    value = members[index],
                                    onValueChange = { members[index] = it },
                                    label = { if (index == 0) Text("Collector") }
                                )
                            }
                        }
                    }
                    TextButton(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        onClick = { if (members.size < 8 && filterdMembers()) members.add("") }
                    )
                    {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add member",
                            tint = if (members.size >= 8 && filterdMembers()) Color.LightGray else Color.DarkGray,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                }
            }
            Card(modifier = Modifier.padding(32.dp))
            {
                Column {
                    Column {
                        Text(
                            "Contribution",
                            style = TextStyle(fontSize = 18.sp),
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(top = 16.dp),
                            value = contribution.value.toString(),
                            onValueChange = { newValue: String ->
                                val intContribution = newValue.toIntOrNull() ?: 0
                                contribution.value = intContribution
                            })
                    }
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Every week ${reward.value} Birr will be given to a random member.",
                    )
                }
            }
        }
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.2f),
            onClick = { if (filterdMembers()) currentScreen.value = "summary" },
        ) {
            Text(
                "Next",
                fontSize = 24.sp,
                color = if (filterdMembers()) Color.White else Color.Gray
            )
        }
    }

}

@Composable
fun SummaryScreen(
    members: MutableList<String>,
    contribution: MutableState<Int>,
    reward: MutableState<Int>,
    currentScreen: MutableState<String>,
) {
    val winner = remember { mutableStateOf("None") }

    val collector = remember { mutableStateOf(members[0]) }

    fun nextWinner() {

        if (members[0] == collector.value) {
            winner.value = collector.value;members.removeAt(0)
            collector.value = "12b32asdf43r"
        } else if (members.isEmpty())
            winner.value = "None"
        else {
            val randomIndex = Random.nextInt(members.size)
            winner.value = members[randomIndex]
            members.removeAt(randomIndex)
        }
    }

    fun restartEqub() {
        currentScreen.value = "welcome"
        members.clear()
        members.add(0, "")
        members.add(1, "")
        members.add(2, "")
    }

    Column(
        modifier = Modifier
            .padding(24.dp)
    ) {
        Text(
            "Equb Summary",
            style = TextStyle(fontSize = 40.sp, color = Color.White),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.DarkGray)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp, bottom = 8.dp)
        )
        Row {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(0.5f)
                    .padding(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.height((members.size * 60).dp)
                ) {
                    item {
                        Text(
                            "Remaining Members",
                            modifier = Modifier.padding(bottom = 8.dp), fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    items(members.size) { index ->
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp),
                            text = (index + 1).toString() + ". " + members[index],
                        )
                    }
                }
                Column {
                    Text(
                        "Contribution",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        text = contribution.value.toString(),
                    )

                }
            }
            Column {
                Button(
                    onClick = { if (members.isNotEmpty()) nextWinner() else restartEqub() }
                ) {
                    Text(if (members.isNotEmpty()) "Roll the dice" else "Restart Equb")
                }

                if (members.isNotEmpty() && winner.value != "None")
                    Text(
                        text = "Congrats ${winner.value}\nYou have won ${reward.value}",
                        fontSize = 24.sp,
                        fontFamily = FontFamily.Monospace
                    )
                else if (members.isEmpty()) {
                    Text(
                        text = "All participants have been rewarded.\nThe Equb is over",
                        fontSize = 22.sp,
                    )
                }

            }
        }
    }
}
