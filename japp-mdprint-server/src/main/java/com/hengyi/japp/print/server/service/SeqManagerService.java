package com.hengyi.japp.print.server.service;

import com.google.common.base.Strings;
import com.hengyi.japp.common.JappCodecUtil;
import com.hengyi.japp.print.server.MyService;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.util.prefs.Preferences;

public final class SeqManagerService {
    public static final Class<?> NODE_FOR_PACKAGE = MyService.class;
    /**
     * 保存用户使用码单的当前序列号
     */
    public static final String PREFIX_SEQ = "SEQUENCE-";
    /**
     * 保存用户使用码单的序列号段
     */
    public static final String PREFIX_SEQS_MIN = "SEQRANGES_MIN-";
    public static final String PREFIX_SEQS_MAX = "SEQRANGES_MAX-";

    public synchronized static String getNextCharg(String bukrs) throws Exception {
        Validate.notBlank(bukrs);
        bukrs = JappCodecUtil.convertUnique(bukrs);
        final long seq = getNextSeq(bukrs);
        return "M" + bukrs + Strings.padStart("" + seq, 6, '0');
    }

    private static long getNextSeq(final String bukrs) throws IOException {
        Preferences prefs = Preferences.userNodeForPackage(NODE_FOR_PACKAGE);
        SeqRange seqRange = getSeqRangeLocal(prefs, bukrs);
        //获取用户当前的序列
        long seq = prefs.getLong(PREFIX_SEQ + bukrs, -1);
        if (seq == -1) {
            seq = seqRange.getMin();
        } else if (seq < seqRange.getMax()) {
            ++seq;
        } else {
            seqRange = getSeqRangeRemote(bukrs);
            updateSeqRange(prefs, bukrs, seqRange);
            seq = seqRange.getMin();
        }
        updateSeq(prefs, bukrs, seq);
        return seq;
    }

    private static SeqRange getSeqRangeLocal(Preferences prefs, String bukrs) throws IOException {
        SeqRange seqRange;
        //获取当前用户允许的序列段
        long seqMin = prefs.getLong(PREFIX_SEQS_MIN + bukrs, -1);
        if (seqMin == -1) {
            seqRange = getSeqRangeRemote(bukrs);
            updateSeqRange(prefs, bukrs, seqRange);
        } else {
            long seqMax = prefs.getLong(PREFIX_SEQS_MAX + bukrs, -1);
            seqRange = new SeqRange(seqMin, seqMax);
        }
        return seqRange;
    }

    /**
     * 去服务器获取新的序列号段
     */
    private static SeqRange getSeqRangeRemote(String bukrs) {
        //TODO 去服务器获取新的序列号段
        SeqRange result = new SeqRange(0, 100);
        return result;
    }

    private static void updateSeq(Preferences prefs, String bukrs, long seq) {
        prefs.putLong(PREFIX_SEQ + bukrs, seq);
    }

    private static void updateSeqRange(Preferences prefs, String bukrs, SeqRange seqRange) {
        prefs.putLong(PREFIX_SEQS_MIN + bukrs, seqRange.getMin());
        prefs.putLong(PREFIX_SEQS_MAX + bukrs, seqRange.getMax());
    }

    static final class SeqRange {

        private final long min;
        private final long max;

        private SeqRange(long min, long max) {
            Validate.isTrue(min >= 0);
            Validate.isTrue(max >= 0);
            Validate.isTrue(max > min);
            this.min = min;
            this.max = max;
        }

        public long getMin() {
            return min;
        }

        public long getMax() {
            return max;
        }
    }
}
