;(function ($) {

    $.fn.dbKeypress = function (targetKeyCode, parameters) {
        let DKP = {
            lastKeyCode: null,
            lastFiredTime: null,
            interval: 350,
            clear: function () {
                DKP.lastKeyCode = DKP.lastFiredTime = null;
            }
        };

        this.each(function () {
            let eventType = (parameters['eventType'] === undefined) ? 'keydown' : parameters['eventType'];
            if (eventType !== 'keydown' && eventType !== 'keydown' && eventType !== 'keyup') {
                return;
            }

            if (parameters['interval'] !== undefined) {
                DKP.interval = parameters['interval'];
            }

            $(this).on(eventType, function (e) {

                let keyCode = e.keyCode || e.which;
                let targetKeyCodes = (typeof (targetKeyCode) == 'number') ? [targetKeyCode] : targetKeyCode;

                if (targetKeyCode != null && $.inArray(keyCode, targetKeyCodes) === -1) {
                    DKP.clear();
                }

                let milliSeconds = (new Date).getTime();

                if (DKP.lastFiredTime != null && (DKP.lastFiredTime + DKP.interval) < milliSeconds) {
                    DKP.clear();
                }

                if (DKP.lastKeyCode != null && DKP.lastKeyCode === keyCode) {
                    let callback = (typeof (parameters) == 'function') ? parameters : parameters['callback'];

                    if (typeof (callback) == 'function') {
                        DKP.clear();
                        callback(e);
                    }
                }
                DKP.lastKeyCode = keyCode;
                DKP.lastFiredTime = milliSeconds;
            });
        });
    }

})(jQuery);
