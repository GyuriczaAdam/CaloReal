package hu.adikaindustries.core.util

sealed class UIEvent{
    object Success:UIEvent()
    object NavigateUp:UIEvent()
    data class ShowSnackBar(val message:UiText) : UIEvent()
}
