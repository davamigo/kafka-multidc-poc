'use strict';

window.onload = function () {

    const enableTooltips = function () {
        $(function () {
            $('[data-toggle="tooltip"]').tooltip();
        });
    };

    const refreshTopics = function () {
        $("div.progress[id^='bar-'][id$='-main']").each(function () {
            const $this = $(this);
            const topicName = $this.data('topic-name');
            refreshTopic(topicName);
        });
    };

    const refreshTopic = function (topicName) {
        jQuery.getJSON('/api/topic/' + topicName + '/stats')
            .done(function (data) {
                const errCount = data.messagesNotProducedBecauseAnErrorCount || 0;
                const senCount = data.messagesSentToProducerCount            || 0;
                const proCount = data.producedCount                          || 0;
                const penCount = data.messagesProducedNotConsumedCount       || 0;
                const conCount = data.consumedCount                          || 0;
                const outCount = data.messagesConsumedNotProducedCount       || 0;
                const total = errCount + senCount + proCount + penCount + conCount + outCount;

                refreshProgressBar('errors',   topicName, errCount, total);
                refreshProgressBar('sent',     topicName, senCount, total);
                refreshProgressBar('produced', topicName, proCount, total);
                refreshProgressBar('consumed', topicName, conCount, total);
                refreshProgressBar('pending',  topicName, penCount, total);
                refreshProgressBar('outofseq', topicName, outCount, total);

                const errMsgs = data.messagesNotProducedBecauseAnError || [];
                const senMsgs = data.messagesSentToProducer            || [];
                const penMsgs = data.messagesProducedNotConsumed       || [];
                const outMsgs = data.messagesConsumedNotProduced       || [];

                refreshMessages('errors',   topicName, errCount, errMsgs);
                refreshMessages('sent',     topicName, senCount, senMsgs);
                refreshMessages('pending',  topicName, penCount, penMsgs);
                refreshMessages('outofseq', topicName, outCount, outMsgs);

                setTimeout(refreshTopic, 250, topicName);
            });
    };

    const refreshProgressBar = function (barName, topicName, val, tot) {
        const per = (tot === 0 || val  === 0) ? 0 : 25 + (75 * val  / tot);
        const $progressBar = $("[id='bar-" + topicName + "-" + barName + "']");
        $progressBar.width(per + "%");
        $progressBar.text(barName + ': ' + val);
    };

    const refreshMessages = function (className, topicName, count, messages) {
        const $list = $("[id='list-" + topicName + "-" + className + "']");
        if (count === 0) {
            setMessages($list, '[]')
        } else {
            let text = '[';
            for (let i = 0; i < messages.length; i++) {
                if (i > 0) {
                    text += ', ';
                }
                text += messages[i];
            }
            text += ']';
            setMessages($list, text)
        }
    };

    const setMessages = function ($list, messages) {
        if ($list.html() !== messages) {
            $list.html(messages);
        }
    };

    enableTooltips();
    refreshTopics();
};
