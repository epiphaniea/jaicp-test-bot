require: lib.js

require: city/city.sc
    module = sys.zb-common

require: where/where.sc
    module = zenbot-common

require: common.js
    module = zenbot-common

theme: /

    state: Start
        intent: /начать
        script:
            $session.score = 0;
            $session.asked = 0;
            log("Score is " + $session.score)
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
            $session.keys = Object.keys($Geography);
            $session.country = $Geography[chooseRandCountryKey($session.keys)];
            $reactions.answer("Угадай столицу {{$session.country.value.genCountry}}");
            $session.asked++;

    state: CityPattern
        q!: * $City *
        script:
            if (checkCapital($parseTree, $session.country) == true) {
                log("Correct answer");
                $session.score++;
                $reactions.answer("Верно!");
            } else {
                $reactions.answer("Неверно. Правильный ответ: {{$session.country.value.name}}");
            }            
        a: Продолжим?
        
        state: Да
           intent: /да
           go!: /Letsplay
        state: Нет
           intent: /нет
           go!: /EndGame

    state: NoMatch
        event!: noMatch
        a: Это не похоже на ответ. Попробуйте еще раз.

    state: EndGame
        intent: /стоп
        a: Спасибо за игру! Твой результат {{$session.score}} из {{$session.asked}}.
        a: Пока!
        EndSession:

    state: reset
        q!: reset
        script:
            $session = {};
            $client = {};
        go!: /

