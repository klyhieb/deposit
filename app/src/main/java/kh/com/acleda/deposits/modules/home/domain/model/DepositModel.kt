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
    @SerializedName("status") var status: String? = null,
    @SerializedName("termId") var termId: String? = null,
    @SerializedName("mm") var mm: String? = null,
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("CurrencyOri") var currency: String? = null,
    @SerializedName("AmountOri") var AmountOri: Float = 0.0f,
    @SerializedName("InterestRateOri") var InterestRateOri: String? = null,
    @SerializedName("ValueDateOri") var ValueDateOri: String? = null,
    @SerializedName("FinalMaturityDateOri") var FinalMaturityDateOri: String? = null,
    @SerializedName("FinalMaturityDateHisOri") var FinalMaturityDateHisOri: String? = null,
    @SerializedName("InterestPeroidStartOri") var InterestPeroidStartOri: String? = null,
    @SerializedName("InterestPeroidEndOri") var InterestPeroidEndOri: String? = null,
    @SerializedName("PeroidOri") var PeroidOri: String? = null,
    @SerializedName("accountIdOri") var accountIdOri: String? = null,
    @SerializedName("remainCycle") var remainCycle: String? = null,
    @SerializedName("termTypeName") var termTypeName: String? = null,
    @SerializedName("termName") var termName: String? = null,
    @SerializedName("termTypeId") var termTypeId: String? = null,
    @SerializedName("interestRate") var interestRate: String? = null,
    @SerializedName("interestRateDetail") var interestRateDetail: String? = null,
    @SerializedName("effectiveDate") var effectiveDate: String? = null,
    @SerializedName("maturityDate") var maturityDate: String? = null,
    @SerializedName("isNeverRenewal") var isNeverRenewal: String? = null,
    @SerializedName("maxRenewalTime") var maxRenewalTime: String? = null,
    @SerializedName("opt") var opt: String? = null,
    @SerializedName("ref") var ref: String? = null,

    // will delete later
    @SerializedName("cif") var cif: String? = null,
    @SerializedName("Capitalize") var Capitalize: String? = null,
    @SerializedName("RollOption") var RollOption: String? = null,
    @SerializedName("RollTerm") var RollTerm: String? = null,
    @SerializedName("companyCode") var companyCode: String? = null,
)
