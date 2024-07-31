// OnboardingAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kerberos.goldylearn.R
import com.kerberos.goldylearn.models.OnboardingItem

class OnboardingAdapter(private val onboardingItems: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val item = onboardingItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }

    class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val onboardingTitle: TextView = itemView.findViewById(R.id.onboardingTitle)
        private val onboardingDescription: TextView = itemView.findViewById(R.id.onboardingDescription)
        private val onboardingImage: ImageView = itemView.findViewById(R.id.onboardingImage)

        fun bind(item: OnboardingItem) {
            onboardingTitle.text = item.title
            onboardingDescription.text = item.description
            onboardingImage.setImageResource(item.imageRes)
        }
    }
}
