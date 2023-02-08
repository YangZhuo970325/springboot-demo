server {
  listen 7081;

  error_log logs/domain-error.log error;
  access_log logs/domain-access.log access;
  default_type text/plain;

  #用户id
  set_by_lua_block $user_id {
      return "zhangsan"
  }

}