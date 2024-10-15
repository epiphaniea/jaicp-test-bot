require: lib.js

require: city/city.sc
    module = sys.zb-common

require: where/where.sc
    module = zenbot-common

require: common.js
    module = zenbot-common

theme: /

    state: Start
        q!: $regex</start>
        script:
            $session.score = 0;
            $session.asked = 0;
        a: Привет! Давай сыграем в игру "Угадай столицу". Начнем?
        
        state: Да
           intent: /да
           go!: /Letsplay
        state: Нет
           intent: /нет
           a: Пока!
           EndSession:

    state: Letsplay
        # сгенерируем случайную страну
        script:
            $session.keys = Object.keys($Countries);
            log("My_country_value_name" + JSON.stringify($Countries[chooseRandCountryKey($session.keys)].value.name));
            $session.country = $Countries[chooseRandCountryKey($session.keys)].value.name;
            $session.score += 1;
            $reactions.answer("Начнём. Угадай столицу\nСтрана {{$session.country}}");

    state: CityPattern
        q!: * $City *
        a: Город: {{$parseTree._City.name}}
        
    state: NoMatch
        event!: noMatch
        a: Это не похоже на ответ. Попробуйте еще раз.

    state: reset
        q!: reset
        script:
            $session = {};
            $client = {};
        go!: /

