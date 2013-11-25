#! /usr/bin/env python

import fileinput
import json

# For each tuple..
for line in fileinput.input():
  title_id, title, has_entities = json.loads(line)
  # We are emitting one variable and one factor for each word.
  if title is not None:
    # print json.dumps(list(set(title.split(" "))))
    for word in set(title.split(" ")):
      # (title_id, word) - The id is automatically assigned.
      print json.dumps([int(title_id), word])