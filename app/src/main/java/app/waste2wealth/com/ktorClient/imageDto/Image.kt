package app.waste2wealth.com.ktorClient.imageDto


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("access_hotlink")
    val accessHotlink: Int?,
    @SerializedName("access_html")
    val accessHtml: Int?,
    @SerializedName("adult")
    val adult: Int?,
    @SerializedName("cloud")
    val cloud: Int?,
    @SerializedName("date_fixed_peer")
    val dateFixedPeer: String?,
    @SerializedName("description")
    val description: Any?,
    @SerializedName("display_height")
    val displayHeight: Int?,
    @SerializedName("display_url")
    val displayUrl: String?,
    @SerializedName("display_width")
    val displayWidth: Int?,
    @SerializedName("expiration")
    val expiration: Int?,
    @SerializedName("extension")
    val extension: String?,
    @SerializedName("file")
    val `file`: File?,
    @SerializedName("filename")
    val filename: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("how_long_ago")
    val howLongAgo: String?,
    @SerializedName("id_encoded")
    val idEncoded: String?,
    @SerializedName("image")
    val image: ImageX?,
    @SerializedName("is_animated")
    val isAnimated: Int?,
    @SerializedName("is_use_loader")
    val isUseLoader: Boolean?,
    @SerializedName("likes")
    val likes: Int?,
    @SerializedName("likes_label")
    val likesLabel: String?,
    @SerializedName("medium")
    val medium: Medium?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("nsfw")
    val nsfw: Int?,
    @SerializedName("original_exifdata")
    val originalExifdata: Any?,
    @SerializedName("original_filename")
    val originalFilename: String?,
    @SerializedName("ratio")
    val ratio: Double?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("size_formatted")
    val sizeFormatted: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("thumb")
    val thumb: Thumb?,
    @SerializedName("time")
    val time: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("title_truncated")
    val titleTruncated: String?,
    @SerializedName("title_truncated_html")
    val titleTruncatedHtml: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("url_seo")
    val urlSeo: String?,
    @SerializedName("url_short")
    val urlShort: String?,
    @SerializedName("url_viewer")
    val urlViewer: String?,
    @SerializedName("url_viewer_preview")
    val urlViewerPreview: String?,
    @SerializedName("url_viewer_thumb")
    val urlViewerThumb: String?,
    @SerializedName("views_hotlink")
    val viewsHotlink: Int?,
    @SerializedName("views_html")
    val viewsHtml: Int?,
    @SerializedName("views_label")
    val viewsLabel: String?,
    @SerializedName("vision")
    val vision: Int?,
    @SerializedName("width")
    val width: Int?
)