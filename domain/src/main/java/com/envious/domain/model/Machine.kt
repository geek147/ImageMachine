package com.envious.domain.model

data class Machine(
    val id: Int,
    val name: String,
    val type: MachineType,
    val qrNumber: Long,
    val lastUpdate: String
)

enum class MachineType {
    LANDSCAPE,
    PORTRAIT,
    PANORAMA,
}
