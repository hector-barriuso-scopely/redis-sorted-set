package com.scopely.sorted.set;

public final class MemberScore implements Comparable<MemberScore> {
  private final String member;
  private final double score;

  public MemberScore(String member, double score) {
    this.member = member;
    this.score = score;
  }

  @Override
  public int compareTo(MemberScore other) {
    return score > other.score ? 1 : score == other.score ? member.compareTo(other.member) : -1;
  }

  public String getMember() {
    return member;
  }

  public double getScore() {
    return score;
  }
}
