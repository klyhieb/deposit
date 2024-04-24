package kh.com.acleda.deposits.modules.home.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DepositRatesModel(
    @SerializedName("status") var status: String? = "",
    @SerializedName("msg") var msg: String? = "",
    @SerializedName("hi_growth") var hiGrowth: DepositRateObjectModel? = DepositRateObjectModel(),
    @SerializedName("hi_income") var hiIncome: DepositRateObjectModel? = DepositRateObjectModel(),
    @SerializedName("long_term") var longTerm: DepositRateObjectModel? = DepositRateObjectModel()
)

@Keep
data class DepositRateObjectModel(
    @SerializedName("ID") var id: String? = "",
    @SerializedName("Label") var label: String? = "",
    @SerializedName("Desc") var desc: String? = "",
    @SerializedName("MinKHRAmount") var minKHRAmount: String? = "",
    @SerializedName("MinUSDAmount") var minUSDAmount: String? = "",
    @SerializedName("ProductRollOverOption") var productRollOverOption: String? = "",
    @SerializedName("RateDetails") var rateDetails: ArrayList<DepositRateDetailsModel>? = ArrayList()
)

@Keep
data class DepositRateDetailsModel(
    @SerializedName("Term") var term: String? = "",
    @SerializedName("Type") var type: String? = "",
    @SerializedName("RateTermMaxRange") var rateTermMaxRange: String? = "",
    @SerializedName("KhrRate") var khrRate: String? = "",
    @SerializedName("UsdRate") var usdRate: String? = ""
)
