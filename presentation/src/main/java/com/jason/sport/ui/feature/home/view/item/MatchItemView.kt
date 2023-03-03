package com.jason.sport.ui.feature.home.view.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jason.domain.entity.Match
import com.jason.domain.entity.Team
import com.jason.sport.R
import com.jason.sport.ui.component.BoldText
import com.jason.sport.ui.component.GlideImage
import com.jason.sport.ui.component.RegularText
import com.jason.sport.util.TinySpace
import com.jason.sport.util.TinySpaceX
import com.jason.sport.util.changeDateFormat

@Composable
fun MatchItemView(
    modifier: Modifier = Modifier,
    match: Match,
    onItemPressed: ((Match) -> Unit)? = null,
) {
    val favoriteResIconId = remember { mutableStateOf(R.drawable.ic_star_border) }
    val reminderResIconId = remember { mutableStateOf(R.drawable.ic_reminder_border) }
    val hasWinner = remember { mutableStateOf(false) }

    LaunchedEffect(match.isFavorite) {
        favoriteResIconId.value = getResFavoriteIconId(match.isFavorite)
    }

    LaunchedEffect(match.isReminder) {
        reminderResIconId.value = getReminderIconId(match.isReminder)
    }

    LaunchedEffect(match.winner) {
        hasWinner.value = match.winner != ""
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 10.dp,
                horizontal = 16.dp
            ),
        backgroundColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(8.dp),
        elevation = 1.dp
    ) {
        ConstraintLayout(
            modifier = modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
                .clickable { onItemPressed?.invoke(match) }
        ) {
            val (favorite, home, timeAndHighlight, away, reminder) = createRefs()

            //            ResImage(
            //                modifier = Modifier
            //                    .size(15.dp)
            //                    .constrainAs(favorite) {
            //                        centerVerticallyTo(parent)
            //                        start.linkTo(parent.start)
            //                    },
            //                resIconId = favoriteResIconId.value,
            //            )

            Column(
                modifier = Modifier.constrainAs(timeAndHighlight) {
                    centerVerticallyTo(parent)
                    centerHorizontallyTo(parent)
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BoldText(
                    content = match.date.changeDateFormat("HH:mm"),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(5.dp))
                RegularText(
                    content = match.date.changeDateFormat("dd MMM"),
                    color = MaterialTheme.colorScheme.secondary
                )

                if (hasWinner.value) {
                    Box(
                        modifier = Modifier
                            .padding(top = TinySpaceX())
                            .clip(RoundedCornerShape(15))
                            .background(color = MaterialTheme.colorScheme.primary)
                            .padding(
                                horizontal = TinySpaceX(),
                                vertical = TinySpace()
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        RegularText(content = stringResource(id = R.string.match_highlight), textAlign = TextAlign.Center)
                    }
                }
            }

            //            ResImage(
            //                resIconId = reminderResIconId.value,
            //                modifier = Modifier
            //                    .size(15.dp)
            //                    .constrainAs(reminder) {
            //                        centerVerticallyTo(parent)
            //                        end.linkTo(parent.end)
            //                    }
            //            )

            TextIcon(
                modifier = Modifier.constrainAs(home) {
                    centerVerticallyTo(parent)
                    end.linkTo(
                        timeAndHighlight.start,
                        margin = 16.dp
                    )
                },
                title = match.home.name,
                url = match.home.logo
            )

            IconText(
                modifier = Modifier.constrainAs(away) {
                    centerVerticallyTo(parent)
                    start.linkTo(
                        timeAndHighlight.end,
                        margin = 16.dp
                    )
                },
                title = match.away.name,
                url = match.away.logo
            )
        }
    }
}

@Preview
@Composable
private fun ItemDefaultPreview() {
    MatchItemView(
        match = Match(
            date = "2022-04-23T18:00:00.000Z",
            description = "Team Cool Eagles vs. Team Red Dragons",
            home = Team(
                id = "767ec50c-7fdb-4c3d-98f9-d6727ef8252b",
                name = "Team Red Dragons",
                logo = "https://tstzj.s3.amazonaws.com/dragons.png",
            ),
            away = Team(
                id = "7b4d8114-742b-4410-971a-500162101158",
                name = "Team Cool Eagles",
                logo = "https://tstzj.s3.amazonaws.com/eagle.png",
            ),
            winner = "Team Red Dragons",
            highlights = "https://tstzj.s3.amazonaws.com/highlights.mp4"
        )
    )
}

@Composable
fun TextIcon(
    modifier: Modifier = Modifier,
    title: String,
    url: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RegularText(
            content = removeTeamText(title),
            modifier = Modifier.padding(end = 10.dp)
        )
        GlideImage(
            imageUrl = url,
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    title: String,
    url: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            imageUrl = url,
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
        )
        RegularText(
            content = removeTeamText(title),
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

private fun getResFavoriteIconId(isFavorite: Boolean): Int {
    return if (isFavorite) {
        R.drawable.ic_star_filled
    } else {
        R.drawable.ic_star_border
    }
}

private fun getReminderIconId(isReminder: Boolean): Int {
    return if (isReminder) {
        R.drawable.ic_reminder_filled
    } else {
        R.drawable.ic_reminder_border
    }
}

private fun removeTeamText(title: String): String {
    return if (title.length > 5 && title.substring(
            0,
            4
        ) == "Team"
    ) {
        title.substring(5)
    } else {
        title
    }
}