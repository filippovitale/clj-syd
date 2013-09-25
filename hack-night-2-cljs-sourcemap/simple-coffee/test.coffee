Array::map = (iterator) ->
  for item, index in this
    iterator.call item, index

one = [1,2,3]
mapped = one.map (item) -> item * 2

console.log mapped

