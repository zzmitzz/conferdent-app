package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_resource

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.toColorInt
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.core.ui.RoseCurveSpinner
import com.turing.conferdent_conferentsmanagement.data.event.models.ResourceItem
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans

// File type enum with icon and color mapping
enum class ResourceFileType(
    val iconRes: Int,
    val backgroundColor: Color
) {
    VIDEO(R.drawable.ic_file_video, Color(0xFFE91E63)),
    IMAGE(R.drawable.ic_file_image, Color(0xFF4CAF50)),
    PDF(R.drawable.ic_file_pdf, Color(0xFFE53935)),
    DOCS(R.drawable.ic_file_docs, Color(0xFF2196F3)),
    POWERPOINT(R.drawable.ic_file_ppt, Color(0xFFFF5722)),
    MAPS(R.drawable.ic_map, Color(0xFF4285F4)),
    OTHER(R.drawable.ic_file_other, Color(0xFF9E9E9E));

    companion object {
        fun fromMimeType(mimeType: String?, resourceType: String?): ResourceFileType {
            // Check resource type first (for maps)
            if (resourceType?.uppercase() == "MAPS") return MAPS
            
            // Check mime type
            return when {
                mimeType == null -> OTHER
                mimeType.startsWith("video/") -> VIDEO
                mimeType.startsWith("image/") -> IMAGE
                mimeType == "application/pdf" -> PDF
                mimeType.contains("word") || 
                mimeType.contains("document") ||
                mimeType == "application/msword" ||
                mimeType == "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> DOCS
                mimeType.contains("presentation") ||
                mimeType.contains("powerpoint") ||
                mimeType == "application/vnd.ms-powerpoint" ||
                mimeType == "application/vnd.openxmlformats-officedocument.presentationml.presentation" -> POWERPOINT
                else -> OTHER
            }
        }
    }
}

// UI model for displaying documents
data class DocumentItem(
    val id: Int,
    val name: String,
    val description: String,
    val urlSource: String,
    val fileType: ResourceFileType,
    val fileSizeBytes: Long? = null,
    val mimeType: String? = null
) {
    companion object {
        fun fromResourceItem(resource: ResourceItem): DocumentItem {
            return DocumentItem(
                id = resource.id,
                name = resource.name ?: "Untitled",
                description = resource.description ?: "",
                urlSource = resource.urlSource ?: "",
                fileType = ResourceFileType.fromMimeType(resource.mimeType, resource.resourceType),
                fileSizeBytes = resource.fileSizeBytes,
                mimeType = resource.mimeType
            )
        }
    }
}

@Composable
fun ScreenResourceEvent(
    eventId: String?,
    viewModel: ResourceVM = hiltViewModel(),
    navigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(eventId) {
        eventId?.let { viewModel.fetchResources(it) }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is ResourceUIState.Loading -> {
                RoseCurveSpinner(color = Color.Black)
            }
            is ResourceUIState.Success -> {
                val documents = (uiState as ResourceUIState.Success).documents
                ScreenResourceEventStateless(
                    documents = documents,
                    navigateBack = navigateBack,
                    onOpenClicked = { doc ->
                        openResource(context, doc)
                    },
                    onDownloadClicked = { doc ->
                        downloadResource(context, doc)
                    }
                )
            }
            is ResourceUIState.Error -> {
                Text(
                    text = (uiState as ResourceUIState.Error).message,
                    color = Color.Red
                )
            }
        }
    }
}

// Open resource in external app based on file type
private fun openResource(context: Context, document: DocumentItem) {
    try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            when (document.fileType) {
                ResourceFileType.MAPS -> {
                    data = Uri.parse(document.urlSource)
                }
                ResourceFileType.PDF -> {
                    setDataAndType(Uri.parse(document.urlSource), "application/pdf")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                ResourceFileType.VIDEO -> {
                    setDataAndType(Uri.parse(document.urlSource), "video/*")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                ResourceFileType.IMAGE -> {
                    setDataAndType(Uri.parse(document.urlSource), "image/*")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                ResourceFileType.DOCS -> {
                    setDataAndType(Uri.parse(document.urlSource), "application/msword")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                ResourceFileType.POWERPOINT -> {
                    setDataAndType(Uri.parse(document.urlSource), "application/vnd.ms-powerpoint")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                ResourceFileType.OTHER -> {
                    data = Uri.parse(document.urlSource)
                }
            }
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(context, intent, null)
    } catch (e: Exception) {
        Toast.makeText(context, "Không tìm thấy ứng dụng để mở tệp này", Toast.LENGTH_SHORT).show()
    }
}

// Download resource to external storage via DownloadManager
private fun downloadResource(context: Context, document: DocumentItem) {
    try {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(document.urlSource)
        
        val request = DownloadManager.Request(uri).apply {
            setTitle(document.name)
            setDescription("Đang tải xuống ${document.name}")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                document.name
            )
            setAllowedOverMetered(true)
            setAllowedOverRoaming(true)
        }
        
        downloadManager.enqueue(request)
        Toast.makeText(context, "Đang tải xuống: ${document.name}", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Lỗi khi tải xuống: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenResourceEventStateless(
    documents: List<DocumentItem> = emptyList(),
    navigateBack: () -> Unit = {},
    onOpenClicked: (DocumentItem) -> Unit = {},
    onDownloadClicked: (DocumentItem) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color("#ECECEE".toColorInt()))
    ) {
        // Back button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .background(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
                .clickable {
                    navigateBack()
                }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Quay lại",
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        }
        
        // Title
        Text(
            text = "Tài liệu",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = JosefinSans,
            color = Color.Black,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )

        if (documents.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Không có tài liệu",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                contentPadding = PaddingValues(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(documents) { document ->
                    DocumentListItem(
                        document = document,
                        onOpenClicked = onOpenClicked,
                        onDownloadClicked = onDownloadClicked
                    )
                }
            }
        }
    }
}

@Composable
fun DocumentListItem(
    document: DocumentItem,
    onOpenClicked: (DocumentItem) -> Unit,
    onDownloadClicked: (DocumentItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onOpenClicked(document) }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // File type icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(document.fileType.backgroundColor.copy(alpha = 0.15f))
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = document.fileType.iconRes),
                    contentDescription = "File Type Icon",
                    tint = document.fileType.backgroundColor,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Text Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = document.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.Black,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = document.description,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 2
                )
                // Show file size if available
                document.fileSizeBytes?.let { size ->
                    Text(
                        text = formatFileSize(size),
                        fontSize = 11.sp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // Download Icon
            IconButton(
                onClick = { onDownloadClicked(document) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_download),
                    contentDescription = "Download",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

// Format file size to human readable format
private fun formatFileSize(bytes: Long): String {
    return when {
        bytes < 1024 -> "$bytes B"
        bytes < 1024 * 1024 -> "${bytes / 1024} KB"
        bytes < 1024 * 1024 * 1024 -> "${bytes / (1024 * 1024)} MB"
        else -> "${bytes / (1024 * 1024 * 1024)} GB"
    }
}