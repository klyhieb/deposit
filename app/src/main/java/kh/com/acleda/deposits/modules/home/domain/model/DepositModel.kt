package kh.com.acleda.deposits.modules.home.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DepositListModel(
    @SerializedName("status") var status: String? = null,
    @SerializedName("listMM") var listMM: ArrayList<DepositItemModel> = arrayListOf(),
    @SerializedName("totalAmountKHR") var totalAmountKHR: Float = 0.0f,
    @SerializedName("totalAmountUSD") var totalAmountUSD: Float = 0.0f,
    @SerializedName("code") var code: String? = null,
    @SerializedName("msg") var msg: String? = null,
    @SerializedName("opt") var opt: String? = null,
    @SerializedName("respDate") var respDate: String? = null,
    @SerializedName("data") var data: String? = null
)

@Keep
data class DepositItemModel(
    @SerializedName("status") var status: String? = "",
    @SerializedName("termId") var termId: String? = "",
    @SerializedName("mm") var mm: String? = "",
    @SerializedName("amount") var amount: String? = "",
    @SerializedName("CurrencyOri") var currency: String? = "",
    @SerializedName("AmountOri") var AmountOri: Float = 0.0f,
    @SerializedName("InterestRateOri") var InterestRateOri: String? = "",
    @SerializedName("ValueDateOri") var ValueDateOri: String? = "",
    @SerializedName("FinalMaturityDateOri") var FinalMaturityDateOri: String? = "",
    @SerializedName("FinalMaturityDateHisOri") var FinalMaturityDateHisOri: String? = "",
    @SerializedName("InterestPeroidStartOri") var InterestPeroidStartOri: String? = "",
    @SerializedName("InterestPeroidEndOri") var InterestPeroidEndOri: String? = "",
    @SerializedName("PeroidOri") var PeroidOri: String? = "",
    @SerializedName("accountIdOri") var accountIdOri: String? = "",
    @SerializedName("remainCycle") var remainCycle: String? = "",
    @SerializedName("termTypeName") var termTypeName: String? = "",
    @SerializedName("termName") var termName: String? = "",
    @SerializedName("termTypeId") var termTypeId: String? = "",
    /*@SerializedName("interestRate") var interestRate: String? = "",
    @SerializedName("interestRateDetail") var interestRateDetail: String? = "",*/
    @SerializedName("effectiveDate") var effectiveDate: String? = "",
    @SerializedName("maturityDate") var maturityDate: String? = "",
    @SerializedName("isNeverRenewal") var isNeverRenewal: String? = "",
    @SerializedName("maxRenewalTime") var maxRenewalTime: String? = "",
    @SerializedName("opt") var opt: String? = "",
    @SerializedName("ref") var ref: String? = "",

    // will delete later
    @SerializedName("cif") var cif: String? = "",
    @SerializedName("Capitalize") var Capitalize: String? = "",
    @SerializedName("RollOption") var RollOption: String? = "",
    @SerializedName("RollTerm") var RollTerm: String? = "",
    @SerializedName("companyCode") var companyCode: String? = "",
)
