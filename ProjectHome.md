# Incubator for various ideas #

## Security ##

**NEW** :
  * [gwt-incubator-security-1.0.1](http://gwt-incubator-lib.googlecode.com/files/gwt-incubator-security-1.0.1.jar) was promoted as a stable release. It includes :
    * GWT 1.7 compatibility
    * Updated dependencies (Transmorph)
    * A SpringLoginHandler class to provide a way to login from the GWT application

Beside this module, a web app is provided to illustrate how to use this security lib in a real (even if simple) app context.


There is no simple way to manage exception thrown from [Spring Security](http://static.springframework.org/spring-security/site/index.html) when using GWT as a GUI. That's why I decided to add the thin layer needed to simplify this integration.

The requirements are :

  * Spring Security (tested with 2.0.4)
  * [GWT-SL](http://gwt-widget.sourceforge.net/) : a Spring-GWT integration library

If you are in this case, or if you can modify your project to reach this state, you will be able to use this new incubator project.

**Source code is now available with documentation**

**A 5 minutes long How-To : SimpleHowTo**

**A quick introduction is available here : GwtIncubatorSecurity**

UPDATE :
- GWT 1.7 compatibility

## TimePicker ##

**NEW** : TimePicker is now compatible with GWT 1.7 : No more deprecated methods used. But it's still in alpha stage as it needs to be cleaned/improved.

The goal behind this project was to provide a component acting as the [jQuery TimePicker](http://haineault.com/media/jquery/ui-timepickr/page/#d-demo-wrapper-1)

[THE demo](http://gwt-incubator-lib.googlecode.com/svn/trunk/GWT-Incubator-Demo/com.gwtincubator.TimePickerEntryPoint/TimePickerEntryPoint.html)

UPDATE :
- GWT 1.7 compatibility