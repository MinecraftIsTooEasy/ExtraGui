package moddedmite.xylose.extragui.util;

import java.util.Arrays;

public class FrameTimeTracker {
    private static final int BUFFER_SIZE = 1000;
    private final long[] frameTimes = new long[BUFFER_SIZE];
    private int index = 0;
    private boolean filled = false;
    private long lastFrame = System.nanoTime();

    public void startFrame() {
        long currentTime = System.nanoTime();
        if (lastFrame > 0) {
            long frameTime = currentTime - lastFrame;
            frameTimes[index] = frameTime;
            index = (index + 1) % BUFFER_SIZE;
            if (index == 0) filled = true;
        }
        lastFrame = currentTime;
    }

    public double calculate1PercentLow() {
        int sampleSize = filled ? BUFFER_SIZE : index;
        if (sampleSize == 0) return 0;

        long[] sortedTimes = Arrays.copyOf(frameTimes, sampleSize);
        Arrays.sort(sortedTimes);

        int worstCount = Math.max(1, sampleSize / 100);
        long worstSum = 0;
        for (int i = sampleSize - worstCount; i < sampleSize; i++) {
            worstSum += sortedTimes[i];
        }

        double avgWorstTime = (double) worstSum / worstCount;
        return 1000000000.0 / avgWorstTime;
    }
}
