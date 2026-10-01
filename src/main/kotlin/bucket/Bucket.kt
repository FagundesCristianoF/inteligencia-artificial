package bucket

class Bucket(private var capacity: Int, private var amount: Int) {
    fun fill(): Bucket {
        val bucket = clone()
        bucket.amount = bucket.capacity
        return bucket
    }

    fun empty(): Bucket {
        val bucket = clone()
        bucket.amount = 0
        return bucket
    }

    fun transfer(target: Bucket): Array<Bucket> {
        val source = clone()
        val dest = target.clone()
        val missing = dest.capacity - dest.amount
        if (missing <= source.amount) {
            dest.amount += missing
            source.amount -= missing
        } else {
            dest.amount = source.amount
            source.amount = 0
        }
        return arrayOf(source, dest)
    }

    fun clone(): Bucket {
        return Bucket(capacity, amount)
    }

    fun getCapacity(): Int {
        return capacity
    }

    fun getAmount(): Int {
        return amount
    }
}
