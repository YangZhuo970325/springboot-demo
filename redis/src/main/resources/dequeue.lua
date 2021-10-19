local zset_key = KEYS[1]
local min_score = KEYS[2]
local max_score = KEYS[3]
local offset = KEYS[4]
local limit = KEYS[5]
local status, type = next(redis.call('TYPE', zset_key))
if status ~= nil and status == 'ok' then
    if type == 'zset' then
        local list = redis.call('ZREVRANGEBYSCORE', zset_key, max_score, min_score, 'LIMIT', offset, limit)
        if list ~= nil and #list > 0 then
            redis.call('ZREM', zset_key, unpack(list))
            return list
            else
        end
    end
end