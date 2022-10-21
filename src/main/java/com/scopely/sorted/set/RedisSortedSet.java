package com.scopely.sorted.set;

import java.util.List;

public interface RedisSortedSet {

  /**
   * Adds the specified member with the specified score to the sorted set. If a specified member is
   * already a member of the sorted set, the score is updated and the element reinserted at the
   * right position to ensure the correct ordering.
   * @param member The specified member
   * @param score The specified score
   * @return 1 when the element is added to the sorted set or 0 when it is updated
   */
  int add(String member, double score);

  /**
   * Returns the specified range of elements in the sorted set
   * @param start First position of the range
   * @param end Last position of the range
   * @return a list of elements in the specified range
   */
  List<String> range(int start, int end);

  /**
   * Returns the rank of member in the sorted set with the scores ordered from high to low.
   * The rank (or index) is 0-based, which means that the member with the highest score has rank 0.
   * @param member The specified member
   * @return The rank of the member in the sorted set, or -1 if the member is not in the sorted set
   */
  int rank(String member);
}
