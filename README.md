# rest_assured_vk_test

To start testing:
1) create standalone application - https://vk.com/editapp?act=create
2) change the following values to current:

EndPoints.java :

USER_ID - your current profile Id

ACCESS_TOKEN - how to take it - https://vk.com/dev/implicit_flow_user

https://oauth.vk.com/authorize?client_id={application_id}&scope=notify,photos,friends,audio,video,notes,pages,docs,status,questions,offers,wall,groups,messages,notifications,stats,ads,offline&redirect_uri=https://api.vk.com/blank.html&display=page&response_type=token

where application_id - id from step 1

