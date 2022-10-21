package com.scopely.sorted.set;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConcurrentRedisSortedSet implements RedisSortedSet {
    private final List<MemberScore> memberScores = new ArrayList<>();

    @Override
    public synchronized int add(String member, double score) {
        final MemberScore newMemberScore = new MemberScore(member, score);
        int result = 1;
        // First, we remove old member, if existed
        for (int i = 0; i < memberScores.size(); i++) {
            final MemberScore memberScore = memberScores.get(i);
            if (member.equals(memberScore.getMember())) {
                memberScores.remove(i);
                result = 0; // We return 0, as this is an update
                break;
            }
        }
        // Then, we add it in the right place
        boolean inserted = false;
        for (int i = 0; i < memberScores.size(); i++) {
            final MemberScore memberScore = memberScores.get(i);
            if (newMemberScore.compareTo(memberScore) > 0) {
                memberScores.add(i, newMemberScore);
                inserted = true;
                break;
            }
        }
        if (!inserted) {
            memberScores.add(newMemberScore);
        }
        return result;
    }

    @Override
    public synchronized List<String> range(int start, int end) {
        return memberScores.subList(start, end).stream().map(MemberScore::getMember).collect(
            Collectors.toList());
    }

    @Override
    public synchronized int rank(String member) {
        int result = -1;

        for (int i = 0; i < memberScores.size(); i++) {
            final MemberScore memberScore = memberScores.get(i);
            if (member.equals(memberScore.getMember())) {
                result = i;
                break;
            }
        }
        return result;
    }
}
