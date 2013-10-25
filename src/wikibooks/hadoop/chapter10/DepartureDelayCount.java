package wikibooks.hadoop.chapter10;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import wikibooks.hadoop.chapter05.DelayCountReducer;
import wikibooks.hadoop.chapter05.DepartureDelayCountMapper;

public class DepartureDelayCount {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		// �Է��� ������ ��� Ȯ��
		if (args.length != 2) {
			System.err.println("Usage: DepartureDelayCount <input> <output>");
			System.exit(2);
		}
		conf.set("mapred.job.queue.name", "queueA");
		// Job �̸� ����
		Job job = new Job(conf, "DepartureDelayCount");

		// ����� ������ ��� ����
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		// Job Ŭ���� ����
		job.setJarByClass(DepartureDelayCount.class);
		// Mapper Ŭ���� ����
		job.setMapperClass(DepartureDelayCountMapper.class);
		// Reducer Ŭ���� ����
		job.setReducerClass(DelayCountReducer.class);

		// ����� ������ ���� ����
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		// ���Ű �� ��°� ���� ����
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.waitForCompletion(true);
	}
}