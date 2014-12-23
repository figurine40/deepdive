#! /usr/bin/env python

# Compute the recall based on labels

import json, csv
task_dir = 'spouse_example-recall/'

labels = json.load(open(task_dir + 'tags.json'))['by_key']
sentences = [l for l in csv.reader(open(task_dir + 'input.csv'))][1:]
rid_exp = {}

for s in sentences:
  relation_id = s[0]
  expectation = float(s[4])
  rid_exp[relation_id] = expectation

# Recall: 
positive_correct = 0
tot_correct = 0
for key in labels:
  if labels[key]['is_correct'] == True:
    tot_correct += 1
    if rid_exp[key] >= 0.9:
      positive_correct += 1

print '# labels: %d' % len(labels)
print '# true positive: %d' % positive_correct
print '# false negative: %d' % (tot_correct - positive_correct)
print 'RECALL: %.3f' % (positive_correct / float(tot_correct))
