package com.example.testassignment.presentation.home.model


data class TransactionUI(
    val id: String,
    val publicId: String,
    val amount: Double,
    val type: String,
    val status: String,
    val origin: String,
    val merchant: MerchantUI,
    val card: CardUI?,
    val account: AccountUI,
    val category: Any?,
    val attachments: List<AttachmentUI>,
    val createDate: String,
    val completeDate: String,
)

data class MerchantUI(
    val name: String?,
    val icon: Any?,
)

data class CardUI(
    val id: String,
    val cardLast4: String,
    val cardName: String,
    val isLocked: Boolean,
    val isTerminated: Boolean,
    val spent: Long,
    val limit: Long,
    val limitType: String,
    val cardHolder: TransactionCardHolderUI,
    val fundingSource: String,
    val issuedAt: String,
)

data class TransactionCardHolderUI(
    val id: String,
    val fullName: String,
    val email: String,
    val logoUrl: String,
)

data class AccountUI(
    val accountName: String,
    val accountLast4: String,
    val accountType: String,
)

data class AttachmentUI(
    val id: String,
    val transactionId: String,
    val externalTransactionId: String,
    val note: Any?,
    val fileUrl: String?,
    val fileName: String?,
    val fileSize: String?,
    val fileType: String?,
    val sourceId: String,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: Any?,
)