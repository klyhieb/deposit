package kh.com.acleda.deposits.modules.depositList.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ViewTermDetailModel(
    @SerializedName("status") var status: String? = null,
    @SerializedName("termId") var termId: String? = null,
    @SerializedName("termName") var termName: String? = null,
    @SerializedName("mm") var mm: String? = null,
    @SerializedName("depositAmount") var depositAmount: String? = null,
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("taxPercentage") var taxPercentage: String? = null,
    @SerializedName("depositAccountName") var depositAccountName: String? = null,
    @SerializedName("depositAccountNumber") var depositAccountNumber: String? = null,
    @SerializedName("depositTerm") var depositTerm: String? = null,
    @SerializedName("interest") var interest: String? = null,
    @SerializedName("autoRenewal") var autoRenewal: String? = null,
    @SerializedName("rolloverTime") var rolloverTime: String? = null,
    @SerializedName("maturityDate") var maturityDate: String? = null,
    @SerializedName("maturityDateDisplay") var maturityDateDisplay: String? = null,
    @SerializedName("ref") var ref: String? = null,
    @SerializedName("companyCode") var companyCode: String? = null,
    @SerializedName("isNeverRenewal") var isNeverRenewal: String? = null,
    @SerializedName("remainCycle") var remainCycle: String? = null,
    @SerializedName("maxRenewalTime") var maxRenewalTime: String? = null,
    @SerializedName("msg") var msg: String? = null
)
