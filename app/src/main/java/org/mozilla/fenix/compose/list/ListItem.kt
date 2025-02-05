/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.compose.list

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.mozilla.fenix.R
import org.mozilla.fenix.compose.Favicon
import org.mozilla.fenix.compose.PrimaryText
import org.mozilla.fenix.compose.SecondaryText
import org.mozilla.fenix.theme.FirefoxTheme
import org.mozilla.fenix.theme.Theme

private val LIST_ITEM_HEIGHT = 56.dp

private val ICON_SIZE = 24.dp

/**
 * List item used to display a label with an optional description text and
 * an optional, interactable icon at the end.
 *
 * @param label The label in the list item.
 * @param description An optional description text below the label.
 * @param onClick Called when the user clicks on the item.
 * @param iconPainter [Painter] used to display a [ListItemIcon] after the list item.
 * @param iconDescription Content description of the icon.
 * @param onIconClick Called when the user clicks on the icon.
 */
@Composable
fun TextListItem(
    label: String,
    description: String? = null,
    onClick: (() -> Unit)? = null,
    iconPainter: Painter? = null,
    iconDescription: String? = null,
    onIconClick: (() -> Unit)? = null,
) {
    ListItem(
        label = label,
        description = description,
        onClick = onClick,
        afterListAction = {
            iconPainter?.let {
                ListItemIcon(
                    painter = iconPainter,
                    iconDescription = iconDescription,
                    onClick = onIconClick,
                )
            }
        }
    )
}

/**
 * List item used to display a label and a [Favicon] with an optional description text and
 * an optional, interactable icon at the end.
 *
 * @param label The label in the list item.
 * @param description An optional description text below the label.
 * @param onClick Called when the user clicks on the item.
 * @param url Website [url] for which the favicon will be shown.
 * @param iconPainter [Painter] used to display a [ListItemIcon] after the list item.
 * @param iconDescription Content description of the icon.
 * @param onIconClick Called when the user clicks on the icon.
 */
@Composable
fun FaviconListItem(
    label: String,
    description: String? = null,
    onClick: (() -> Unit)? = null,
    url: String,
    iconPainter: Painter? = null,
    iconDescription: String? = null,
    onIconClick: (() -> Unit)? = null,
) {
    ListItem(
        label = label,
        description = description,
        onClick = onClick,
        beforeListAction = {
            Favicon(
                url = url,
                size = ICON_SIZE,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        },
        afterListAction = {
            iconPainter?.let {
                ListItemIcon(
                    painter = iconPainter,
                    iconDescription = iconDescription,
                    onClick = onIconClick,
                )
            }
        }
    )
}

/**
 * List item used to display a label and an icon at the beginning with an optional description
 * text and an optional, interactable icon at the end.
 *
 * @param label The label in the list item.
 * @param description An optional description text below the label.
 * @param onClick Called when the user clicks on the item.
 * @param beforeIconPainter [Painter] used to display a [ListItemIcon] before the list item.
 * @param beforeIconDescription Content description of the icon.
 * @param afterIconPainter [Painter] used to display a [ListItemIcon] after the list item.
 * @param afterIconDescription Content description of the icon.
 * @param onAfterIconClick Called when the user clicks on the icon.
 */
@Composable
fun IconListItem(
    label: String,
    description: String? = null,
    onClick: (() -> Unit)? = null,
    beforeIconPainter: Painter,
    beforeIconDescription: String? = null,
    afterIconPainter: Painter? = null,
    afterIconDescription: String? = null,
    onAfterIconClick: (() -> Unit)? = null,
) {
    ListItem(
        label = label,
        description = description,
        onClick = onClick,
        beforeListAction = {
            ListItemIcon(
                painter = beforeIconPainter,
                iconDescription = beforeIconDescription,
                onClick = null,
            )
        },
        afterListAction = {
            afterIconPainter?.let {
                ListItemIcon(
                    painter = afterIconPainter,
                    iconDescription = afterIconDescription,
                    onClick = onAfterIconClick,
                )
            }
        }
    )
}

/**
 * Base list item used to display a label with an optional description text and
 * the flexibility to add custom UI to either end of the item.
 *
 * @param label The label in the list item.
 * @param description An optional description text below the label.
 * @param onClick Called when the user clicks on the item.
 * @param beforeListAction Optional Composable for adding UI before the list item.
 * @param afterListAction Optional Composable for adding UI to the end of the list item.
 */
@Composable
private fun ListItem(
    label: String,
    description: String? = null,
    onClick: (() -> Unit)? = null,
    beforeListAction: @Composable RowScope.() -> Unit = {},
    afterListAction: @Composable RowScope.() -> Unit = {},
) {
    Row(
        modifier = when (onClick != null) {
            true -> Modifier.clickable { onClick() }
            false -> Modifier
        }.then(
            Modifier.defaultMinSize(minHeight = LIST_ITEM_HEIGHT)
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        beforeListAction()

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .weight(1f),
        ) {
            PrimaryText(
                text = label,
                fontSize = 16.sp,
                maxLines = 1,
            )

            description?.let {
                SecondaryText(
                    text = description,
                    fontSize = 14.sp,
                    maxLines = 1,
                )
            }
        }

        afterListAction()
    }
}

/**
 * Base Icon Composable used to display a [Painter] in a [ListItem].
 *
 * @param painter [Painter] used to display the icon.
 * @param iconDescription Content description of the icon.
 * @param onClick Called when the user clicks on the icon.
 */
@Composable
private fun ListItemIcon(
    painter: Painter,
    iconDescription: String? = null,
    onClick: (() -> Unit)? = null,
) {
    Box(
        modifier = when (onClick != null) {
            true -> Modifier.clickable { onClick() }
            false -> Modifier
        }.then(
            Modifier
                .padding(horizontal = 16.dp)
                .defaultMinSize(minHeight = LIST_ITEM_HEIGHT),
        ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painter,
            contentDescription = iconDescription,
            modifier = Modifier.size(ICON_SIZE),
            tint = FirefoxTheme.colors.iconPrimary,
        )
    }
}

@Composable
@Preview(name = "TextListItem", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun TextListItemPreview() {
    FirefoxTheme(theme = Theme.getTheme(isPrivate = false)) {
        Box(Modifier.background(FirefoxTheme.colors.layer1)) {
            TextListItem(label = "Label only")
        }
    }
}

@Composable
@Preview(name = "TextListItem with a description", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun TextListItemWithDescriptionPreview() {
    FirefoxTheme(theme = Theme.getTheme(isPrivate = false)) {
        Box(Modifier.background(FirefoxTheme.colors.layer1)) {
            TextListItem(
                label = "Label + description",
                description = "Description text"
            )
        }
    }
}

@Composable
@Preview(name = "TextListItem with a right icon", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun TextListItemWithIconPreview() {
    FirefoxTheme(theme = Theme.getTheme(isPrivate = false)) {
        Box(Modifier.background(FirefoxTheme.colors.layer1)) {
            TextListItem(
                label = "Label + right icon",
                iconPainter = painterResource(R.drawable.ic_menu),
                iconDescription = "click me",
            )
        }
    }
}

@Composable
@Preview(name = "IconListItem", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun IconListItemPreview() {
    FirefoxTheme(theme = Theme.getTheme(isPrivate = false)) {
        Box(Modifier.background(FirefoxTheme.colors.layer1)) {
            IconListItem(
                label = "Left icon list item",
                beforeIconPainter = painterResource(R.drawable.ic_folder_icon),
                beforeIconDescription = "click me",
            )
        }
    }
}

@Composable
@Preview(name = "IconListItem with an interactable right icon", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun IconListItemWithRightIconPreview() {
    FirefoxTheme(theme = Theme.getTheme(isPrivate = false)) {
        Box(Modifier.background(FirefoxTheme.colors.layer1)) {
            IconListItem(
                label = "Left icon list item + right icon",
                beforeIconPainter = painterResource(R.drawable.ic_folder_icon),
                beforeIconDescription = null,
                afterIconPainter = painterResource(R.drawable.ic_menu),
                afterIconDescription = "click me",
                onAfterIconClick = { println("icon click") },
            )
        }
    }
}

@Composable
@Preview(name = "FaviconListItem with a right icon and onClicks", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun FaviconListItemPreview() {
    FirefoxTheme(theme = Theme.getTheme(isPrivate = false)) {
        Box(Modifier.background(FirefoxTheme.colors.layer1)) {
            FaviconListItem(
                label = "Favicon + right icon + clicks",
                description = "Description text",
                onClick = { println("list item click") },
                url = "",
                iconPainter = painterResource(R.drawable.ic_menu),
                onIconClick = { println("icon click") },
            )
        }
    }
}
