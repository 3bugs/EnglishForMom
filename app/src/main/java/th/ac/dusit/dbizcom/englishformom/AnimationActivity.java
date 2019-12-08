package th.ac.dusit.dbizcom.englishformom;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import th.ac.dusit.dbizcom.englishformom.etc.Utils;
import th.ac.dusit.dbizcom.englishformom.model.Animation;
import th.ac.dusit.dbizcom.englishformom.model.Sentence;

public class AnimationActivity extends AppCompatActivity {

    private List<Animation> mAnimationList = null;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        mAnimationList = new ArrayList<>();
        mAnimationList.add(new Animation(
                1, "morning", "", 31.2f
        ));
        mAnimationList.add(new Animation(
                2, "school", "", 33.4f
        ));
        mAnimationList.add(new Animation(
                3, "playground", "", 35.6f
        ));
        mAnimationList.add(new Animation(
                4, "eat", "", 37.8f
        ));
        mAnimationList.add(new Animation(
                5, "weekend", "", 39.0f
        ));

        AnimationListAdapter adapter = new AnimationListAdapter(
                this,
                mAnimationList,
                new AnimationListener() {
                    @Override
                    public void onClickAnimation(Animation animation) {
                        Utils.showShortToast(AnimationActivity.this, animation.category);
                    }
                }
        );

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpacingDecoration(this));
        mRecyclerView.setAdapter(adapter);
    }

    /*@Override
    public void onClick(View v) {
        String videoUrl = null;

        switch (v.getId()) {
            case R.id.morning_image_view:
                videoUrl = getString(R.string.video_url_1);
                break;
            case R.id.school_image_view:
                //videoUrl = "";
                break;
            case R.id.playground_image_view:
                videoUrl = getString(R.string.video_url_2);
                break;
            case R.id.eat_image_view:
                //videoUrl = "";
                break;
            case R.id.holiday_text_view:
                videoUrl = "";
                //break;
        }

        if (videoUrl == null) {
            Utils.showLongToast(this, "ยังไม่เสร็จครับ");
            return;
        }

        Intent intent = new Intent(AnimationActivity.this, VideoActivity.class);
        intent.putExtra(KEY_VIDEO_URL, videoUrl);
        startActivity(intent);
    }*/

    public interface AnimationListener {
        void onClickAnimation(Animation animation);
    }

    private static class AnimationListAdapter extends RecyclerView.Adapter<AnimationListAdapter.ViewHolder> {

        private static final int VIEW_TYPE_NORMAL = 0;
        private static final int VIEW_TYPE_BUTTON = 1;

        private final Context mContext;
        private final List<Animation> mAnimationList;
        private final AnimationListener mListener;

        AnimationListAdapter(Context context, List<Animation> animationList, AnimationListener listener) {
            mContext = context;
            mAnimationList = animationList;
            mListener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_animation, parent, false
            );
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder h, int position) {
            final Animation animation = mAnimationList.get(position);

            h.mAnimation = animation;
            h.mDurationTextView.setText(String.valueOf(animation.videoDuration));

            int categoryImageResId = 0;
            switch (animation.category) {
                case Sentence.CATEGORY_MORNING:
                    categoryImageResId = R.drawable.title_category_morning;
                    //h.mVideoImageView.setImageResource(R.drawable.thumb_animation_01);
                    break;
                case Sentence.CATEGORY_SCHOOL:
                    categoryImageResId = R.drawable.title_category_school;
                    //h.mVideoImageView.setImageResource(R.drawable.thumb_animation_02);
                    break;
                case Sentence.CATEGORY_PLAYGROUND:
                    categoryImageResId = R.drawable.title_category_playground;
                    //h.mVideoImageView.setImageResource(R.drawable.thumb_animation_03);
                    break;
                case Sentence.CATEGORY_EAT:
                    categoryImageResId = R.drawable.title_category_eat;
                    //h.mVideoImageView.setImageResource(R.drawable.thumb_animation_04);
                    break;
                case Sentence.CATEGORY_WEEKEND:
                    categoryImageResId = R.drawable.title_category_weekend;
                    //h.mVideoImageView.setImageResource(R.drawable.thumb_animation_05);
                    break;
            }
            h.mCategoryImageView.setImageResource(categoryImageResId);

            /*CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(mContext);
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();

            Glide.with(mContext)
                    .load(ApiClient.IMAGE_BASE_URL.concat(animation.coverImage))
                    .placeholder(circularProgressDrawable)
                    .into(holder.mProjectImageView);*/
        }

        @Override
        public int getItemCount() {
            return mAnimationList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private final ImageView mVideoImageView;
            private final ImageView mCategoryImageView;
            private final TextView mDurationTextView;

            private Animation mAnimation;

            ViewHolder(View itemView) {
                super(itemView);

                mVideoImageView = itemView.findViewById(R.id.video_image_view);
                mCategoryImageView = itemView.findViewById(R.id.category_image_view);
                mDurationTextView = itemView.findViewById(R.id.duration_text_view);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onClickAnimation(mAnimation);
                    }
                });
            }
        }
    }

    public class SpacingDecoration extends RecyclerView.ItemDecoration {

        private final static int MARGIN_TOP_IN_DP = 0;
        private final static int MARGIN_BOTTOM_IN_DP = 16;
        private final int mMarginTop, mMarginBottom;

        SpacingDecoration(@NonNull Context context) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            mMarginTop = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    MARGIN_TOP_IN_DP,
                    metrics
            );
            mMarginBottom = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    MARGIN_BOTTOM_IN_DP,
                    metrics
            );
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent,
                                   @NonNull RecyclerView.State state) {
            final int itemPosition = parent.getChildAdapterPosition(view);
            if (itemPosition == RecyclerView.NO_POSITION) {
                return;
            }
            if (itemPosition == 0) {
                outRect.top = mMarginTop;
            }
            final RecyclerView.Adapter adapter = parent.getAdapter();
            if ((adapter != null) && (itemPosition == adapter.getItemCount() - 1)) {
                outRect.bottom = mMarginBottom;
            }
        }
    }
}
