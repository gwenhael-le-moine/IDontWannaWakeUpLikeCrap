debug:
	ant clean && ant debug install && adb shell am start -a android.intent.action.MAIN -n com.gwenhael.idontwannawakeuplikecrap/com.gwenhael.idontwannawakeuplikecrap.IDontWannaWakeUpLikeCrapActivity
