package kh.com.acleda.deposits.modules.home.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DepositListModel(
    @SerializedName("listMM") var listMM: ArrayList<DepositItemModel> = arrayListOf(),
    @SerializedName("totalAmountKHR") var totalAmountKHR: Float = 0.0f,
    @SerializedName("totalAmountUSD") var totalAmountUSD: Float = 0.0f,
    @SerializedName("taxPercentage") var taxPercentage: Float = 0.0f
)

@Keep
data class DepositItemModel(
    @SerializedName("depositAccountName") var depositAccountName: String = "",
    @SerializedName("depositAccountNumber") var depositAccountNumber: String = "",
    @SerializedName("termId") var termId: String = "",
    @SerializedName("status") var status: String = "",
    @SerializedName("cif") var cif: String = "",
    @SerializedName("currency") var currency: String = "",
    @SerializedName("depositAmount") var depositAmount: Float = 0.0f,
    @SerializedName("interestRate") var interestRate: String = "",
    @SerializedName("rolloverTime") var rolloverTime: String = "",
    @SerializedName("depositTerm") var depositTerm: String = "",
    @SerializedName("mm") var mm: String = "",
    @SerializedName("accountIdOri") var accountIdOri: String = "",
    @SerializedName("ref") var ref: String = "",
    @SerializedName("companyCode") var companyCode: String = "",
    @SerializedName("opt") var opt: String = "",
    @SerializedName("isNeverRenewal") var isNeverRenewal: String = "",
    @SerializedName("remainCycle") var remainCycle: String = "",
    @SerializedName("termTypeName") var termTypeName: String = "",
    @SerializedName("termName") var termName: String = "",
    @SerializedName("termTypeId") var termTypeId: String = "",
    @SerializedName("amount") var amount: String = "",
    @SerializedName("effectiveDate") var effectiveDate: String = "",
    @SerializedName("maturityDate") var maturityDate: String = "",
    @SerializedName("maxRenewalTime") var maxRenewalTime: String = "",
    @SerializedName("autoRenewal") var autoRenewal: String = "",
)
