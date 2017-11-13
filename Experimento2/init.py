import redis
r = redis.Redis();
r.set('admin','fbc71ce36cc20790f2eeed2197898e71')
r.set('superuser','admin')