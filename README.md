mondaine-app
============

This is the App Repository. The top level directory structure is:

* __apps__: The directory containing the apps. Subdirectories are named after the architecture used for implementation
  *__android__: Android dalvik project
  *__ios__ : IPhone/Tablet project
* __libs__: libs the _apps_ depend on
  * __maivic_comm__: maivic communication prrotocol library. This Library handles the protocol itself (sending and receiving pbf messages)
  * __maivic_comm_protocol__: maivic protocol library implementing the specific communication, including protoc generated code
* __design__: design specifications for the app. This includes the psd files specifying the view elements and composition
  


