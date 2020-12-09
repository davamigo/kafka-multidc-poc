'use strict';

window.onload = function () {

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
                const produced = data.producedCount || 0;
                const consumed = data.consumedCount || 0;
                const pending = data.pendingMessagesCount || 0;
                const outofseq = data.outOfSeqMessagesCount || 0;
                const errors = data.unableToProduceCount || 0;

                const total1 = produced + consumed;
                const pro = (total1 === 0) ? 0 : (100 * produced / total1);
                const con = (total1 === 0) ? 0 : (100 * consumed / total1);
                const total2 = pending + outofseq + errors;
                const pen = (total2 === 0 || pending === 0) ? 0 : 25 + (75 * pending / total2);
                const out = (total2 === 0 || outofseq === 0) ? 0 : 25 + (75 * outofseq / total2);
                const err = (total2 === 0 || errors === 0) ? 0 : 25 + (75 * errors / total2);

                refreshProgressBar('produced', topicName, pro, produced);
                refreshProgressBar('consumed', topicName, con, consumed);
                refreshProgressBar('pending', topicName, pen, pending);
                refreshProgressBar('outofseq', topicName, out, outofseq);
                refreshProgressBar('errors', topicName, err, errors);

                refreshMessages('pending', topicName, pending);
                refreshMessages('outofseq', topicName, outofseq);
                refreshMessages('errors', topicName, errors);

                setTimeout(refreshTopic, 250, topicName);
            });
    };

    const refreshProgressBar = function (barName, topicName, per, val) {
        const $progressBar = $("[id='bar-" + topicName + "-" + barName + "']");
        $progressBar.width(per + "%");
        $progressBar.text(barName + ': ' + val);
    };

    const refreshMessages = function (className, topicName, count) {
        const $list = $("span[id='list-" + topicName + "-" + className + "']");
        if (count === 0) {
            setMessages($list, '[]')
        } else {
            getMessages($list, className, topicName)
        }
    };

    const getMessages = function ($list, className, topicName) {
        jQuery.getJSON('/api/topic/' + topicName + '/' + className)
            .done(function (data) {
                let text = '';
                for (let i = 0; i < data.length; i++) {
                    if (i > 0) {
                        text += ', ';
                    }
                    text += data[i];
                }
                setMessages($list, text);
            });
    };

    const setMessages = function ($list, messages) {
        if ($list.html() !== messages) {
            $list.html(messages);
        }
    };

    refreshTopics();
};
