package com.example.testassignment.remote.responsebody

import com.google.gson.annotations.SerializedName

data class RootTransactionResponseBody(
    @SerializedName("transactions") val transactions: List<TransactionResponseBody>,
)

data class TransactionResponseBody(
    @SerializedName("id") val id: String,
    @SerializedName("publicId") val publicId: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("type") val type: String,
    @SerializedName("status") val status: String,
    @SerializedName("origin") val origin: String,
    @SerializedName("merchant") val merchant: MerchantResponseBody,
    @SerializedName("card") val card: CardResponseBody?,
    @SerializedName("account") val account: AccountResponseBody,
    @SerializedName("category") val category: Any?,
    @SerializedName("attachments") val attachments: List<AttachmentResponseBody>,
    @SerializedName("createDate") val createDate: String,
    @SerializedName("completeDate") val completeDate: String,
)

data class MerchantResponseBody(
    @SerializedName("name") val name: String?,
    @SerializedName("icon") val icon: Any?,
)

data class AccountResponseBody(
    @SerializedName("accountName") val accountName: String,
    @SerializedName("accountLast4") val accountLast4: String,
    @SerializedName("accountType") val accountType: String,
)

data class AttachmentResponseBody(
    @SerializedName("id") val id: String,
    @SerializedName("transactionId") val transactionId: String,
    @SerializedName("externalTransactionId") val externalTransactionId: String,
    @SerializedName("note") val note: Any?,
    @SerializedName("fileUrl") val fileUrl: String?,
    @SerializedName("fileName") val fileName: String?,
    @SerializedName("fileSize") val fileSize: String?,
    @SerializedName("fileType") val fileType: String?,
    @SerializedName("sourceId") val sourceId: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("deletedAt") val deletedAt: Any?
)
