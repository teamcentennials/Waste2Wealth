package app.waste2wealth.com.ktorClient.imageDto


import com.google.gson.annotations.SerializedName

data class Resource(
    @SerializedName("chain")
    val chain: Chain?,
    @SerializedName("chain_code")
    val chainCode: ChainCode?
)