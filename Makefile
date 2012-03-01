default:
	$(MAKE) clean debug run
clean:
	ant clean
debug:
	ant debug install
release:
	ant release
sign:
	jarsigner -verbose bin/IDontWannaWakeUpLikeCrap-release-unsigned.apk android
	zipalign -v 4 bin/IDontWannaWakeUpLikeCrap-release-unsigned.apk bin/IDontWannaWakeUpLikeCrap-release.apk

run:
	adb shell am start -a android.intent.action.MAIN -n com.gwenhael.idontwannawakeuplikecrap/com.gwenhael.idontwannawakeuplikecrap.IDontWannaWakeUpLikeCrapActivity
