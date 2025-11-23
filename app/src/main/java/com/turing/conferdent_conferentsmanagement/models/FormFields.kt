package com.turing.conferdent_conferentsmanagement.models

import androidx.compose.runtime.Stable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable

data class FieldRange (

    @SerialName("min" ) val min : String? = null,
    @SerialName("max" ) val max : String? = null

)


@Stable
@Serializable
data class FormFields(
    @SerialName("_id"                    ) val Id                  : String?           = null,
    @SerialName("form_id"                ) val formId              : String?           = null,
    @SerialName("is_primary_key"         ) val isPrimaryKey        : Boolean?          = null,
    @SerialName("can_edit"               ) val canEdit             : Boolean?          = null,
    @SerialName("field_label"            ) val fieldLabel          : String?           = null,
    @SerialName("field_description"      ) val fieldDescription    : String?           = null,
    @SerialName("field_type"             ) val fieldType           : String?           = null,
    @SerialName("field_options"          ) val fieldOptions        : List<String> = listOf(),
    @SerialName("field_has_other_option" ) val fieldHasOtherOption : Boolean?          = null,
    @SerialName("field_range"            ) val fieldRange          : FieldRange?       = FieldRange(),
    @SerialName("field_extensions"       ) val fieldExtensions     : List<String> = listOf(),
    @SerialName("required"               ) val required            : Boolean?          = null,
    @SerialName("position"               ) val position            : Int?              = null

)